import { from, Observable, map } from "rxjs";
import { PUBLIC_API_URL } from "$env/static/public";
import { EErrorCode } from "$lib/shared/enums/error-code.enum";
import { EUserRole } from "$lib/shared/enums/user-role.enum";
import type { AxiosResponse } from "axios";
import type { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
import type { IQueryResult } from "$lib/shared/handlers/query/query-result.interface";
import type { IQuery } from "$lib/shared/handlers/query/query.interface";
import type { IUser } from "./list-users.interface";

export class ListUsersQuery implements IQuery<IUser[]> {
  constructor(){};
  
  execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<IUser[]>> {
    const url = `${PUBLIC_API_URL}/queries/admin/users/list-all-users`;
    return from(queriesHandler.axios.get<IQueryResult<IUser[]>>(url)).pipe(
      map((result: AxiosResponse<IQueryResult<IUser[]>>) => {
        if(!result || !result.data || !result.data.resultData){
          return { resultData: [], errorCode: EErrorCode.none } as IQueryResult<IUser[]>;
        }
        result.data.resultData = result.data.resultData.map((user) => ({
          ...user,
          roleUser: EUserRole[user.roleUser as unknown as keyof typeof EUserRole]
        }));
        return result.data;
      })
    );
  }
}