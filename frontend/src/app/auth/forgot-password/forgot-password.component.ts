import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../../shared/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordForm: FormGroup = new FormGroup({});

  constructor(private builder: FormBuilder, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.forgotPasswordForm = this.builder.group({
      email: this.builder.control('', Validators.compose([Validators.required, Validators.email]))
    })
  }

}
