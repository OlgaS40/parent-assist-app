import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../shared/service/auth.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {
  verificationCode: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService,
  ) {
  }

  ngOnInit(): void {
    this.router.navigate(['auth']).then(r =>
      this.extractVerificationCode());
  }

  private extractVerificationCode() {
    // Extract the verification code from the URL parameter
    this.route.queryParams.subscribe(params => {
      this.verificationCode = params['code'];
      if (this.verificationCode) {
        this.verifyRegistration();
      } else {
        this.toastr.error('Invalid verification code.');
      }
    });
  }

  private verifyRegistration() {
    this.authService.verifyRegistration(this.verificationCode).subscribe(
      () => {
        this.toastr.success("Congratulation, your account is active! Now you can login!", 'Success!', {
          positionClass: 'toast-top-center',
          timeOut: 0,
          extendedTimeOut: 0
        });
      }, err => {
        this.toastr.error(err.error.message, 'Failed!', {
          positionClass: 'toast-top-center',
          timeOut: 0,
          extendedTimeOut: 0
        });
      }
    )
    ;
  }
}
