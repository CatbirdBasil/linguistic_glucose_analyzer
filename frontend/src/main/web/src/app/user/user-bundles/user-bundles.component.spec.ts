import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserBundlesComponent} from './user-bundles.component';

describe('UserBundlesComponent', () => {
  let component: UserBundlesComponent;
  let fixture: ComponentFixture<UserBundlesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserBundlesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserBundlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
