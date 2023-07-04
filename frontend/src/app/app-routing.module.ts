import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ActivitiesComponent} from './activities/activities.component';
import {DashboardComponent} from './admin-panel/pages/dashboard/dashboard.component';
import {MaterialToysComponent} from './material-toys/material-toys.component';
import {MilestonesComponent} from './milestones/milestones.component';
import {ParentsComponent} from './parents/parents.component';
import {SkillsComponent} from './skills/skills.component';
import {UsersComponent} from './admin-panel/pages/users/users.component';
import {ProductsComponent} from './admin-panel/pages/products/products.component';
import {SubscriptionsComponent} from './admin-panel/pages/subscriptions/subscriptions.component';
import {CashInComponent} from './admin-panel/pages/cash-in/cash-in.component';
import {BlogComponent} from "./pages/blog/blog.component";
import {LandingPageComponent} from "./pages/landing-page/landing-page.component";
import {AppPageComponent} from "./pages/app-page/app-page.component";
import {AuthComponent} from "./auth/auth.component";
import {VerificationComponent} from "./auth/verification/verification.component";
import {authGuard} from "./guard/auth.guard";
import {ProfileComponent} from "./profile/profile.component";

const routes: Routes = [
  {path: '', component: LandingPageComponent},
  {path: 'parentAssistApp', component: AppPageComponent},
  {path: 'blog', component: BlogComponent},
  {path: 'auth', component: AuthComponent},
  {path: 'auth/verify', component: VerificationComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [authGuard]},
  {path: 'activities', component: ActivitiesComponent, canActivate: [authGuard]},
  {path: 'material&toys', component: MaterialToysComponent, canActivate: [authGuard]},
  {path: 'milestones', component: MilestonesComponent, canActivate: [authGuard]},
  {path: 'parents', component: ParentsComponent, canActivate: [authGuard]},
  {path: 'skills', component: SkillsComponent, canActivate: [authGuard]},
  {path: 'admin-panel', component: DashboardComponent, canActivate: [authGuard]},
  {path: 'admin-panel/products', component: ProductsComponent, canActivate: [authGuard]},
  {path: 'admin-panel/subscriptions', component: SubscriptionsComponent, canActivate: [authGuard]},
  {path: 'admin-panel/cash-in', component: CashInComponent, canActivate: [authGuard]},
  {path: 'admin-panel/users', component: UsersComponent, canActivate: [authGuard]},
  {path: '**', redirectTo: '', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
