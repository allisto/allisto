import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientIdComponent } from './patient-id.component';

describe('PatientIdComponent', () => {
  let component: PatientIdComponent;
  let fixture: ComponentFixture<PatientIdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientIdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
