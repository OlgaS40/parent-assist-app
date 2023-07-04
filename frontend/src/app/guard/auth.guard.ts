import {Router} from '@angular/router';
import {AuthService} from "../shared/service/auth.service";
import {inject} from "@angular/core";

export const authGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.user) {
    return true;
  }

  // Redirect to the login page
  return router.parseUrl('/auth');
};
