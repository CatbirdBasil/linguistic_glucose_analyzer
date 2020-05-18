import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "@services/auth.service";


@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})


export class IndexComponent implements OnInit {

  // nothingFound: boolean = false;

  //-----------------
  filterForm: FormGroup;
  public submitted = false;

  loading = false;

  constructor(private formBuilder: FormBuilder,
              //-----
              //-----
              private router: Router,
              private authService: AuthService) {
  }

  ngOnInit() {

    this.filterForm = this.initSearchForm();

    window.scrollTo(0, 0);
  }

  initSearchForm(): FormGroup {
    return this.formBuilder.group({
      name: ["", [Validators.minLength(1), Validators.maxLength(100)]]
    });
  }

  get authorized() {
    return this.authService.token != null;
  }


  navigateToTestPage(id) {
    this.router.navigate(['test/' + id]);
  }

}

