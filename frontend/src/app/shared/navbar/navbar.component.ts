import {Component, OnInit} from '@angular/core';
import {User} from "../model/user";
import {AuthService} from "../service/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit  {
  user: User | null = null;
  constructor(private authService: AuthService) {
    this.authService.user?.subscribe((x) => (this.user = x));
  }
  ngOnInit(): void {

  }

  logout() {
    this.authService.logout();
  }
}
