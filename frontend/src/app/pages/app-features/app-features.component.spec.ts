import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppFeaturesComponent } from './app-features.component';

describe('AppFeaturesComponent', () => {
  let component: AppFeaturesComponent;
  let fixture: ComponentFixture<AppFeaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppFeaturesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppFeaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
