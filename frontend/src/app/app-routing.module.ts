import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/material.module';
import { ActivitiesComponent } from './activities/activities.component';
import { DashboardComponent } from './admin-panel/pages/dashboard/dashboard.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { MaterialToysComponent } from './material-toys/material-toys.component';
import { MilestonesComponent } from './milestones/milestones.component';
import { ParentsComponent } from './parents/parents.component';
import { SkillsComponent } from './skills/skills.component';
import { UsersComponent } from './admin-panel/pages/users/users.component';
import { ProductsComponent } from './admin-panel/pages/products/products.component';
import { SubscriptionsComponent } from './admin-panel/pages/subscriptions/subscriptions.component';
import { CashInComponent } from './admin-panel/pages/cash-in/cash-in.component';
import {BlogComponent} from "./pages/blog/blog.component";
import {AboutComponent} from "./pages/about/about.component";
import {LandingPageComponent} from "./pages/landing-page/landing-page.component";
import {AppPageComponent} from "./pages/app-page/app-page.component";
import {AuthComponent} from "./auth/auth.component";

const routes: Routes = [
  {path:'',component:LandingPageComponent},
  {path:'parentAssistApp',component:AppPageComponent},
  {path:'blog',component:BlogComponent},
  {path:'auth',component:AuthComponent},
  {path:'activities',component:ActivitiesComponent},
  {path:'material&toys',component:MaterialToysComponent},
  {path:'milestones',component:MilestonesComponent},
  {path:'parents',component:ParentsComponent},
  {path:'skills',component:SkillsComponent},
  {path:'admin-panel', component:DashboardComponent},
  {path:'admin-panel/products', component:ProductsComponent},
  {path:'admin-panel/subscriptions', component:SubscriptionsComponent},
  {path:'admin-panel/cash-in', component:CashInComponent},
  {path:'admin-panel/users',component:UsersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
