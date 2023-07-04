import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SignUpRequest} from "../shared/model/signUpRequest";
import {LoginRequest} from "../shared/model/loginRequest";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {Role} from "../shared/model/user";
import {AuthService} from "../shared/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs/operators";
import {ForgotPasswordRequest} from "../shared/model/forgotPasswordRequest";
import {FacebookLoginProvider, SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";
import {Oauth2SignInUpRequest} from "../shared/model/oauth2SignInUpRequest";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  signUpForm: FormGroup = new FormGroup({});
  loginForm: FormGroup = new FormGroup({});
  forgotPasswordForm: FormGroup = new FormGroup({});

  defaultRole = {name: Role.USER, value: "user"};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  isSignUpSubmit: boolean = false;
  isLoginSubmit: boolean = false;
  isForgotPasswordSubmit: boolean = false;
  loginStatusError: boolean = false;
  keepMeSignInStatus: boolean = false;
  socialUser: SocialUser = new SocialUser();
  loggedIn: boolean = false;
  checkedTab: string = 'tab-1';
  isGoogleSignIn: boolean = false;
  isGoogleSignUp: boolean = false;

  constructor(private builder: FormBuilder, private toastr: ToastrService,
              private service: AuthService,
              private route: ActivatedRoute, private router: Router,
              private socialAuthService: SocialAuthService
  ) {
  }

  ngOnInit(): void {
    window.scrollTo(0, 0);

    this.signUpForm = this.builder.group({
      username: this.builder.control('', Validators.compose([Validators.required, Validators.minLength(4), Validators.pattern(/^\S*$/)])),
      password: this.builder.control('', Validators.compose([Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')])),
      repeatPassword: this.builder.control('', Validators.required),
      email: this.builder.control('', Validators.compose([Validators.required, Validators.email]))
    })

    this.socialAuthService.authState.subscribe((user) => {
      this.socialUser = user;
      this.loggedIn = (user != null);
      if (user != null) {
        this.signWithGoogle(user);
      }
    });

    this.loginForm = this.builder.group({
      username: this.builder.control('', Validators.required),
      password: this.builder.control('', Validators.required),
      keepMeSignIn: this.builder.control(true)
    });

    this.forgotPasswordForm = this.builder.group({
      username: this.builder.control('', Validators.required),
      email: this.builder.control('', Validators.compose([Validators.required, Validators.email]))
    });
  }

  getErrorMessage(formName: string, field: string) {
    const form = this.getForm(formName)
    const control = form.get(field);

    if (field === 'username' && control?.hasError("minlength")) {
      return "Username must be at least 4 characters long";
    }
    if (field === 'username' && control?.hasError("pattern")) {
      return "Username must not contain spaces";
    }

    if (field === 'password' && control?.hasError("pattern")) {
      return "Provide strong password: it should be minimum of 8 characters and contain at least: one lowercase and one uppercase letter, one digit, at least one special character from the set [$@$!%*?&].";
    }

    if (field === 'repeatPassword' && this.incorrectPasswordRepeat() && this.isSignUpSubmit) {
      return "Passwords do not match";
    }

    if (field === 'email' && control?.hasError("email")) {
      return "Invalid email format";
    }

    return control?.hasError("required")
      ? "You must enter " + field
      : "";
  }

  isFieldValid(formName: string, field: string): boolean {
    const form = this.getForm(formName);
    const control = form.get(field);
    if (form === this.signUpForm && this.isSignUpSubmit ||
      form === this.loginForm && this.isLoginSubmit ||
      form === this.forgotPasswordForm && this.isForgotPasswordSubmit) {
      return (control?.valid ?? false);
    }
    return true;
  }

  private getForm(formName: string) {
    let form: FormGroup = new FormGroup({});
    switch (formName) {
      case "signup":
        form = this.signUpForm;
        break;
      case "login":
        form = this.loginForm;
        break;
      case "forgotPassword":
        form = this.forgotPasswordForm;
        break;
      default:
        console.log("It's an unknown form.");
    }
    return form;
  }

  onSignIn() {
    if (this.loginForm.valid) {
      const loginRequest: LoginRequest = {
        username: this.loginForm.get('username')?.value,
        password: this.loginForm.get('password')?.value
      };
      const keepMeSignIn = this.loginForm.get('keepMeSignIn')?.value;
      this.keepMeSignInStatus = keepMeSignIn;
      console.log(loginRequest);
      this.login(loginRequest, keepMeSignIn);
    }
  }

  private login(loginRequest: LoginRequest, keepMeSignIn: boolean) {
    this.isLoginSubmit = true;
    this.service
      .login(loginRequest, keepMeSignIn)
      .pipe(first())
      .subscribe({
        next: () => {
          this.loginStatusError = false;
          this.isLoginSubmit = false;
          this.router.navigateByUrl("/activities").then();
        },
        error: (err) => {
          console.log(err);
          console.log("Invalid credentials");
          this.loginStatusError = true;
          if (err.status === 0 && err.statusText === 'Unknown Error') {
            this.toastr.error('Please try again later.', 'Network connection error');
          } else if (err.error.exception === 'BadCredentialsException') {
            this.toastr.error('Credential you entered are wrong, try again.');
          } else if (err.error === 'User is disabled') {
            this.toastr.error('Please complete registration process, access the confirmation link from your email address.', 'User is still disabled!');
          } else {
            this.toastr.error(err.error.message);
          }
        },
      });
  }

  onSignUp() {
    this.isSignUpSubmit = true;

    if (this.signUpForm.valid) {
      const signUpRequest: SignUpRequest = {
        username: this.signUpForm.get('username')?.value,
        email: this.signUpForm.get('email')?.value,
        password: this.signUpForm.get('password')?.value,
        role: [this.defaultRole.name]
      };

      this.service.registerUser(signUpRequest).subscribe({
        next: data => {
          console.log(data);
          this.isSuccessful = true;
          this.isSignUpFailed = false;
          this.isSignUpSubmit = false;
          this.toastr.success("Complete Registration: Check your email and click on the verification link.", 'Registered Successfully!', {
            positionClass: 'toast-top-center',
            timeOut: 0,
            extendedTimeOut: 0
          });
          this.reset('signup')
          this.router.navigate(['auth']);
        },
        error: err => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
          this.reset('signup');
          this.isSignUpSubmit = false;
          if (err.status === 0 && err.statusText === 'Unknown Error') {
            this.toastr.error('Please try again later.', 'Network connection error');
          } else if (err.status === 500) {
            this.toastr.error('Oops, smth went wrong. Please try again later.');
          } else {
            this.toastr.error(this.errorMessage);
          }
        },
      })
    } else {
      this.toastr.warning("Please enter valid data!")
    }
  }

  incorrectPasswordRepeat() {
    return this.signUpForm.get('password')?.value !== this.signUpForm.get('repeatPassword')?.value;
  }

  reset(formName: string) {
    const form = this.getForm(formName);
    form.reset();
  }

  onForgotPassword() {
    this.isForgotPasswordSubmit = true;

    if (this.forgotPasswordForm.valid) {
      const currentUrl = window.location.href;
      const newUrl = new URL(currentUrl);
      const baseUrl = `${newUrl.protocol}//${newUrl.hostname}${newUrl.port ? `:${newUrl.port}` : ''}`;
      const desiredEndpoint = "/auth";
      const url = `${baseUrl}${desiredEndpoint}`;
      const forgotRequest: ForgotPasswordRequest = {
        username: this.forgotPasswordForm.get('username')?.value,
        email: this.forgotPasswordForm.get('email')?.value,
        url: url
      };
      this.service.forgotPassword(forgotRequest).subscribe({
        next: data => {
          this.isForgotPasswordSubmit = false;
          this.toastr.success("Check your email for a new password to log in to your account.", 'Password changed Successfully!', {
            positionClass: 'toast-top-center'
          });
          this.reset('forgotPassword');
          this.closeModal('forgotPassword');
          this.router.navigate(['auth']);
        },
        error: err => {
          this.errorMessage = err.error.message;
          if (err.status === 0 && err.statusText === 'Unknown Error') {
            this.toastr.error('Please try again later.', 'Network connection error');
          } else if (err.status === 500) {
            this.toastr.error('Oops, smth went wrong. Please try again later.');
          } else {
            this.toastr.error(this.errorMessage);
          }
        },
      })
    } else {
      this.toastr.warning("Please enter valid data!")
    }
  }

  private closeModal(modalId: string): void {
    const modal = document.getElementById(modalId);
    const modalBackdrop = document.querySelector('.modal-backdrop') as HTMLElement;
    ;
    if (modal !== null) {
      modal.style.display = 'none';
    }
    if (modalBackdrop !== null) {
      modalBackdrop.style.display = 'none';
    }
  }

  signInWithFB() {
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID).then((socialUser) => {
      console.log(socialUser);
      const facebookSignInUpRequest: Oauth2SignInUpRequest = {
        token: socialUser.authToken
      };
      this.service.loginWithFacebook(facebookSignInUpRequest).subscribe({
          next: () => {
            this.loginStatusError = false;
            this.router.navigateByUrl("/activities").then();
          },
          error: (err) => {
            console.log(err);
            console.log("Invalid credentials");
            this.loginStatusError = true;
            if (err.status === 0 && err.statusText === 'Unknown Error') {
              this.toastr.error('Please try again later.', 'Network connection error');
            } else if (err.status === 500) {
              this.toastr.error('Oops, smth went wrong. Please try again later.');
            } else {
              this.toastr.error(err.error.message);
            }
          },
        }
      );
    });
  }

  signWithGoogle(socialUser: any) {
    if (socialUser.provider === 'GOOGLE') {
      const googleSignInUpRequest: Oauth2SignInUpRequest = {
        token: socialUser.idToken
      };

      console.log(this.socialUser);
      if (this.checkedTab === 'tab-2') {
        console.log(googleSignInUpRequest);
        console.log(this.checkedTab);
        this.service.signUpWithGoogle(googleSignInUpRequest).subscribe({
          next: data => {
            console.log(data);
            this.isSuccessful = true;
            this.isSignUpFailed = false;
            this.toastr.success("Please visit Sign In page to login", 'Registered Successfully!', {
              positionClass: 'toast-top-center',
              timeOut: 0,
              extendedTimeOut: 0
            });
            this.router.navigate(['auth']);
          },
          error: err => {
            this.errorMessage = err.error.message;
            this.isSignUpFailed = true;
            if (err.status === 0 && err.statusText === 'Unknown Error') {
              this.toastr.error('Please try again later.', 'Network connection error');
            } else if (err.status === 500) {
              this.toastr.error('Oops, smth went wrong. Please try again later.');
            } else {
              this.toastr.error(this.errorMessage);
            }
          },
        })
      } else if (this.checkedTab === 'tab-1') {
        console.log(this.checkedTab);
        this.service.loginWithGoogle(googleSignInUpRequest).subscribe({
            next: () => {
              console.log("is logged in");
              this.loginStatusError = false;
              this.router.navigateByUrl("/activities").then();
            },
            error: (err) => {
              console.log(err);
              console.log("Invalid credentials");
              this.loginStatusError = true;
              if (err.status === 0 && err.statusText === 'Unknown Error') {
                this.toastr.error('Please try again later.', 'Network connection error');
              } else if (err.status === 500) {
                this.toastr.error('Oops, smth went wrong. Please try again later.');
              } else {
                this.toastr.error(err.error.message);
              }
            },
          }
        );
      }
    }
  }

  signUpWithFB() {
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID).then((socialUser) => {
      this.registerUserWithFB(socialUser);
    });
  }

  private registerUserWithFB(socialUser: SocialUser) {
    const facebookSignInUpRequest: Oauth2SignInUpRequest = {
      token: socialUser.authToken
    };
    this.service.signUpWithFacebook(facebookSignInUpRequest).subscribe({
      next: data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.toastr.success("Please visit Sign In page to login", 'Registered Successfully!', {
          positionClass: 'toast-top-center',
          timeOut: 0,
          extendedTimeOut: 0
        });
        this.router.navigate(['auth']).then();
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        if (err.status === 0 && err.statusText === 'Unknown Error') {
          this.toastr.error('Please try again later.', 'Network connection error');
        } else if (err.status === 500) {
          this.toastr.error('Oops, smth went wrong. Please try again later.');
        } else {
          this.toastr.error(this.errorMessage);
        }
      },
    });
  }
}
