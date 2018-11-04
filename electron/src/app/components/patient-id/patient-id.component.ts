import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-patient-id',
  templateUrl: './patient-id.component.html',
  styleUrls: ['./patient-id.component.scss']
})
export class PatientIdComponent implements OnInit {

  reference = "hey";
  
  constructor() { }
  ngOnInit() {
  }

  replace(value :string){
    this.reference =value;
  }

}
