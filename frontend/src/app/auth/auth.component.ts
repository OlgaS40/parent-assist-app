import {Component, OnInit} from '@angular/core';
import {SignUpRequest} from "../shared/model/signUpRequest";
import {LoginRequest} from "../shared/model/loginRequest";
import {FormBuilder, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  constructor(private builder: FormBuilder, private toastr: ToastrService) {

  }

  signUpForm = this.builder.group({
    username: this.builder.control('', Validators.compose([Validators.required, Validators.minLength(5), Validators.pattern(/^\S*$/)])),
    password: this.builder.control('', Validators.compose([Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')])),
    repeatPassword: this.builder.control('', Validators.required),
    email: this.builder.control('', Validators.compose([Validators.required, Validators.email]))
  })

  ngOnInit(): void {
    window.scrollTo(0, 0);
  }

  onSignIn() {

  }

  onSignUp() {
    if (this.signUpForm.valid) {

    }
  }
}
