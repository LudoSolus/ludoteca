import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import type { ICommandResult } from './command-result.interface';
import type { ICommand } from './command.interface';

// import { ToastService } from '../../services/toast.service';

export class CommandsHandlerService {
  constructor(
    // public http: HttpClient,
    // public toastService: ToastService,
  ) { }

  handle(command: ICommand, isLoading: boolean = true): Observable<ICommandResult> {
    return command.execute(this).pipe(
      catchError(err => {
        // this.toastService.show(err?.error?.errorMessage || 'Ocorreu um erro na requisição.', 'error');
        return throwError(() => err);
      }),
    );
  }
}
