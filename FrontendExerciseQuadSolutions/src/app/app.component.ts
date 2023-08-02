import { emitDistinctChangesOnlyDefaultValue } from '@angular/compiler';
import { Component } from '@angular/core';
import { EMPTY, empty } from 'rxjs';
import { ApiService } from 'src/services/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  questions: any;
  selectedAnswers: any[] = new Array(5);
  answers: any[] = new Array;

  constructor(private apiService: ApiService) { }

  ngOnInit() {
    this.apiService.getQuestions().subscribe(
      (response) => {
        console.log(response);

        // Handle the API response and map it to the model
        this.questions = response.map((item: any) => ({
          category: item.category,
          type: item.type,
          difficulty: item.difficulty,
          question: item.question,
          answers: item.answers
        }));
      },
      (error) => {
        // Handle errors
        console.error(error);
      }
    );
  }

  categoryEncoded(question: String) {
    return question.replace(/\s+/g, '_').replace(/:/g, '');
  }

  checkAnswers() {
    const questionCount = this.selectedAnswers.reduce((count, item) => {
      return count + 1;
    }, 0);

    if (questionCount == 5) {
      // ALL QUESTIONS HAVE BEEN FILLED IN!      
      this.apiService.postAnswers(this.selectedAnswers).subscribe((res) => this.answers = res);
           
    } else {
      console.log("Not everything is filled in!");
    }
  }

  onRadioButtonChange(index: any, selectedAnswer: string) {
    this.selectedAnswers[index] = selectedAnswer;
  }
}