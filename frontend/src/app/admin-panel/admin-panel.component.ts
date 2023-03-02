import { Component, ElementRef, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  title = 'admin-panel';
  constructor(private el:ElementRef){}
  ngOnInit(): void {

    let alldrpdwn = document.querySelectorAll('.dropdow-container');
    console.log(alldrpdwn,'alldrpdwn#')
    alldrpdwn.forEach((item:any) => {
      const a = item.parentElement.querySelector('a:first-child');
      console.log(a,'a#');
      a.addEventListener('click', (e:any) => {
        e.preventDefault();
        this.el.nativeElement.classList.toggle('active');
        item.classList.toggle('show');

      });
    })
  }

  //responsive menu
  responsiveMenu:any;
  //responsive main content
  responsiveContent:any;
  defaultStatus=true;
  openNav(status: any){
    if(status === this.defaultStatus){
      this.responsiveMenu={
        'display':'block'
      }
      this.responsiveContent={
        'margin-left':'150px'
      }
      this.defaultStatus = false;
    }else{
      this.responsiveMenu={
        'display':null
      }
      this.responsiveContent={
        'margin-left':null
      }
      this.defaultStatus = true;
    }

  }

}
