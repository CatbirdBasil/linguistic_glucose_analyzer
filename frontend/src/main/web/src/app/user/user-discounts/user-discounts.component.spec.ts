import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserDiscountsComponent} from './user-discounts.component';

describe('UserDiscountsComponent', () => {
  let component: UserDiscountsComponent;
  let fixture: ComponentFixture<UserDiscountsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDiscountsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDiscountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
