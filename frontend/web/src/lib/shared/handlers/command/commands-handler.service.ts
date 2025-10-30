import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import type { ICommandResult } from './command-result.interface';
import type { ICommand } from './command.interface';
import type { Axios, AxiosResponse } from 'axios';
import { toast } from 'svoast';

export class CommandsHandlerService {
  constructor(
    public axios: Axios,
  ) { }

  handle(command: ICommand): Observable<AxiosResponse<ICommandResult>> {
    return command.execute(this).pipe(
      catchError(err => {
        toast.error(err?.response?.data?.errorMessage || 'Ocorreu um erro na requisição.', { closable: true });
        return throwError(() => err);
      }),
    );
  }
}
