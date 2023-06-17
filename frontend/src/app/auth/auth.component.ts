import {Component, HostListener, OnInit} from '@angular/core';
import {SignUpRequest} from "../shared/model/signUpRequest";
import {LoginRequest} from "../shared/model/loginRequest";
import {FormBuilder, FormGroup, Validators, ɵFormGroupValue, ɵTypedOrUntyped} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {Role} from "../shared/model/user";
import {AuthService} from "../shared/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs/operators";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  signUpForm: FormGroup = new FormGroup({});
  loginForm: FormGroup = new FormGroup({});

  defaultRole = {name: Role.USER, value: "user"};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  isSignUpSubmited: boolean = false;
  loginStatusError: boolean = false;
  keepMeSignInStatus: boolean = false;

  constructor(private builder: FormBuilder, private toastr: ToastrService,
              private service: AuthService,
              private route: ActivatedRoute, private router: Router
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

    this.loginForm = this.builder.group({
      username: this.builder.control('', Validators.required),
      password: this.builder.control('', Validators.required),
      keepMeSignIn: this.builder.control(true)

    })
  }

  getErrorMessage(formName: string, field: string) {
    const form = formName === 'signup' ? this.signUpForm : this.loginForm;
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

    if (field === 'repeatPassword' && this.incorrectPasswordRepeat() && this.isSignUpSubmited) {
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
    const form = formName === 'signup' ? this.signUpForm : this.loginForm;
    const control = form.get(field);
    if (this.isSignUpSubmited) {
      return (control?.valid ?? false);
    }
    return true;
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
    this.service
      .login(loginRequest, keepMeSignIn)
      .pipe(first())
      .subscribe({
        next: () => {
          this.loginStatusError = false;
          this.router.navigate(["/activities"]);
        },
        error: (error) => {
          console.log(error);
          console.log("Invalid credentials");
          this.toastr.error(this.errorMessage);
          this.loginStatusError = true;
        },
      });
  }

  onSignUp() {
    this.isSignUpSubmited = true;

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
          this.isSignUpSubmited = false;
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
          if (err.status === 0 && err.statusText === 'Unknown Error') {
            this.toastr.error('Please try again later.','Network connection error');
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
    const form = formName === 'signup' ? this.signUpForm : this.loginForm;
    form.reset();
  }

  @HostListener('window:beforeunload', ['$event'])
  onBeforeUnload(event: BeforeUnloadEvent) {
    if(!this.keepMeSignInStatus){
      this.service.logout()
    }
    this.toastr.success("You are closing the app. See you next time.", 'Good Buy!');
  }
}
