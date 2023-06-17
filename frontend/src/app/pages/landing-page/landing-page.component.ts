import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../shared/service/auth.service";

declare const AOS: any;

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit{

  constructor(private authService: AuthService) {
  }
  ngOnInit(): void {
    const storedUser  = localStorage.getItem('user');

    if (storedUser) {
      const user = JSON.parse(storedUser);
      const username = user.username;
      const password = user.password;
      this.authService.login(user, true);
    }
    AOS.init({
      offset: 120,
      delay: 0,
      duration: 900,
      easing: 'ease',
      once: false,
      mirror: false,
      anchorPlacement: 'top-bottom'
    });
  }

}
