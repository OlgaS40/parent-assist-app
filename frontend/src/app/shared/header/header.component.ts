import {Component, OnInit} from '@angular/core';
import {Router, NavigationEnd} from '@angular/router';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  currentUrl: string = '';
  menuOpen: boolean = false;
  responsive: boolean = false;

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = event.url;
      }
    });
  }

  isCurrentPage(url: string): boolean {
    return this.currentUrl === url;
  }

  login() {
    // Code to trigger login function
  }

  signUp() {
    // Code to trigger sign Up function
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
    this.responsive = !this.responsive;
  }
}
