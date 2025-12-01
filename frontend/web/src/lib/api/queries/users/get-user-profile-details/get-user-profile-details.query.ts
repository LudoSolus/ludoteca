import { PUBLIC_API_URL } from '$env/static/public';
import { EErrorCode } from '$lib/shared/enums/error-code.enum';
import type { EUserRole } from '$lib/shared/enums/user-role.enum';
import type { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
import type { IQueryResult } from '$lib/shared/handlers/query/query-result.interface';
import type { IQuery } from '$lib/shared/handlers/query/query.interface';
import type { AxiosResponse } from 'axios';
import { Observable, from, map } from 'rxjs';
import type { IGetUserProfileDetailsResponse } from './get-user-profile-details.interface';
import { stringIsValid } from '$lib/shared/helpers/string-is-valid';

export class GetUserProfileDetailsQuery implements IQuery<IGetUserProfileDetailsResponse> {
	constructor() {}

	execute(
		queriesHandler: QueriesHandlerService
	): Observable<IQueryResult<IGetUserProfileDetailsResponse>> {
		const url = `${PUBLIC_API_URL}/queries/users/profile-user`;
		return from(queriesHandler.axios.get<IQueryResult<IGetUserProfileDetailsResponse>>(url)).pipe(
			map((result: AxiosResponse<IQueryResult<IGetUserProfileDetailsResponse>>) => {
				if (!result || !result.data || !result.data.resultData)
					return {
						resultData: {},
						errorCode: EErrorCode.none
					} as IQueryResult<IGetUserProfileDetailsResponse>;

				result.data.resultData = {
					...result.data.resultData,
					birthDate: new Date(result.data.resultData.birthDate)
				};
				return result.data;
			})
		);
	}
}
