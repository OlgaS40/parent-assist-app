import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaterialToysComponent } from './material-toys.component';

describe('MaterialToysComponent', () => {
  let component: MaterialToysComponent;
  let fixture: ComponentFixture<MaterialToysComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MaterialToysComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MaterialToysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
