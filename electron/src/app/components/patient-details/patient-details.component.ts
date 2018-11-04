import { Component, OnInit, Input } from '@angular/core';
import { AngularFireDatabase } from 'angularfire2/database'

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  @Input() ref: string;
  name = "";
  dob = "";
  gender = "";
  bloodGroup = "";
  database: any;
  medication: any[];
  vaccines: any[];
  autism = "";

  ngOnChanges(changes) {
    this.fetchFromFirebase();
  }

  constructor(public db: AngularFireDatabase) {
    this.database = db;
    this.fetchFromFirebase();
  }

  ngOnInit() {
  }


  fetchFromFirebase() {

    this.database.list('/' + this.ref).valueChanges().subscribe(courses => {
      this.name = courses[5];
      this.dob = courses[2];
      this.gender = courses[3];
      this.bloodGroup = courses[1];
      this.autism = courses[0];
    });

    this.database.list('/' + this.ref + '/medical_history').valueChanges().subscribe(medicines => {
      this.medication = medicines;

    });


    this.database.list('/' + this.ref + '/vaccine_taken').valueChanges().subscribe(vac => {
      this.vaccines = vac;

    });

  }

  addVacc(){
    this.database.list('/'+ this.ref+ '/vaccine_taken').push("The baby was given a treatment of milk on this day");
  }
  addMed(){
    this.database.list('/'+ this.ref+ '/medical_history').push("The baby was given a treatment of milk on this day");
  }

}
