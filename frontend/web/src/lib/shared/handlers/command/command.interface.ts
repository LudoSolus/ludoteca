import { Observable } from 'rxjs';
import { ICommandResult } from './command-result.interface';
import { CommandsHandlerService } from './commands-handler.service';

export interface ICommand {
  execute(handler: CommandsHandlerService): Observable<ICommandResult>;
}
