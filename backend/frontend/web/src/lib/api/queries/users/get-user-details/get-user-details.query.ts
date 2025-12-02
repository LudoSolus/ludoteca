import { PUBLIC_API_URL } from "$env/static/public";
import { EUserRole } from "$lib/shared/enums/user-role.enum";
import { EErrorCode } from "$lib/shared/enums/error-code.enum";
import type { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
import type { IQueryResult } from "$lib/shared/handlers/query/query-result.interface";
import type { IQuery } from "$lib/shared/handlers/query/query.interface";
import type { AxiosResponse } from "axios";
import { Observable, from, map } from "rxjs";
import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';

export class GetUserDetailsQuery implements IQuery<IGetUserDetails> {
    constructor(private userId: string) {}

    execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<IGetUserDetails>> {
        const url = `${PUBLIC_API_URL}/queries/admin/users/${this.userId}/get-user`;
        return from(queriesHandler.axios.get<IQueryResult<IGetUserDetails>>(url)).pipe(
            map((result: AxiosResponse<IQueryResult<IGetUserDetails>>) => {
                if (!result || !result.data || !result.data.resultData)
                    return {
                        resultData: {},
                        errorCode: EErrorCode.none
                    } as IQueryResult<IGetUserDetails>;

                result.data.resultData = {
                    ...result.data.resultData,
                    userType: EUserRole[result.data.resultData.userType as unknown as keyof typeof EUserRole],
                };
                return result.data;
            })
        );
    }
}
