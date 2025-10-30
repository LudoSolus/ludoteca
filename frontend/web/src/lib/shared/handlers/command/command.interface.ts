import { Observable } from 'rxjs';
import { CommandsHandlerService } from './commands-handler.service';
import type { ICommandResult } from './command-result.interface';
import type { AxiosResponse } from 'axios';

export interface ICommand {
  execute(handler: CommandsHandlerService): Observable<AxiosResponse<ICommandResult>>;
}
