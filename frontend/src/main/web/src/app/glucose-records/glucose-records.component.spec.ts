import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlucoseRecordsComponent } from './glucose-records.component';

describe('GlucoseRecordsComponent', () => {
  let component: GlucoseRecordsComponent;
  let fixture: ComponentFixture<GlucoseRecordsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlucoseRecordsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlucoseRecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
