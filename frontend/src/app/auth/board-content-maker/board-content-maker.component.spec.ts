import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardContentMakerComponent } from './board-content-maker.component';

describe('BoardContentMakerComponent', () => {
  let component: BoardContentMakerComponent;
  let fixture: ComponentFixture<BoardContentMakerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardContentMakerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardContentMakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
