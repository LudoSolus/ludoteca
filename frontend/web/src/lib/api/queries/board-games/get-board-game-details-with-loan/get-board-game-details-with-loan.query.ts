import { PUBLIC_API_URL } from "$env/static/public";
import { ECategory } from "$lib/shared/enums/category.enum";
import { EErrorCode } from "$lib/shared/enums/error-code.enum";
import type { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
import type { IQueryResult } from "$lib/shared/handlers/query/query-result.interface";
import type { IQuery } from "$lib/shared/handlers/query/query.interface";
import type { AxiosResponse } from "axios";
import { Observable, from, map } from "rxjs";
import type { IGetBoardGameDetailsWithLoanResponse } from "./get-board-game-details-with-loan.interface";

export class GetBoardGameDetailsWithLoanQuery implements IQuery<IGetBoardGameDetailsWithLoanResponse> {
    constructor(private boardGameId: string) {}

    execute(
        queriesHandler: QueriesHandlerService
    ): Observable<IQueryResult<IGetBoardGameDetailsWithLoanResponse>> {
        const url = `${PUBLIC_API_URL}/queries/admin/games/${this.boardGameId}/details`;
        return from(queriesHandler.axios.get<IQueryResult<IGetBoardGameDetailsWithLoanResponse>>(url)).pipe(
            map((result: AxiosResponse<IQueryResult<IGetBoardGameDetailsWithLoanResponse>>) => {
                if (!result || !result.data || !result.data.resultData)
                    return {
                        resultData: {},
                        errorCode: EErrorCode.none
                    } as IQueryResult<IGetBoardGameDetailsWithLoanResponse>;

                result.data.resultData = {
                    ...result.data.resultData,
                    category: ECategory[result.data.resultData.category as unknown as keyof typeof ECategory],
                    loanHistory: result.data.resultData.loanHistory.map((loan) => ({
                        ...loan,
                        loanDate: new Date(loan.loanDate)
                    }))
                };
                return result.data;
            })
        );
    }
}