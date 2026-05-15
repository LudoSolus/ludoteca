import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { IRegisteredUsersCountResponse } from './registered-users-count.interface';

export class RegisteredUsersCountQuery implements IQuery<IRegisteredUsersCountResponse> {
	constructor() {}

	execute(queriesHandler: QueriesHandlerService): Observable<IQueryResult<IRegisteredUsersCountResponse>> {
		const url = `${PUBLIC_API_URL}/queries/admin/dashboard/registered-users-count`;
		return from(queriesHandler.axios.get<IQueryResult<IRegisteredUsersCountResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<IRegisteredUsersCountResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return { resultData: { totalUsers: 0 }, errorCode: EErrorCode.none } as unknown as IQueryResult<IRegisteredUsersCountResponse>;

				return result.data;
			})
		);
	}
}
