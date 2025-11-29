import { PUBLIC_API_URL } from "$env/static/public";
import type { ECategory } from "$lib/shared/enums/category.enum";
import { EErrorCode } from "$lib/shared/enums/error-code.enum";
import type { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
import type { IQueryResult } from "$lib/shared/handlers/query/query-result.interface";
import type { IQuery } from "$lib/shared/handlers/query/query.interface";
import type { AxiosResponse } from "axios";
import { Observable, from, map } from "rxjs";
import type { IGetBoardGameByBarcodeResonse } from "./get-board-game-by-barcode.interface";

export class GetBoardGameByBarcodeQuery implements IQuery<IGetBoardGameByBarcodeResonse> {
    constructor(private barcode: string) {}

    execute(
        queriesHandler: QueriesHandlerService
    ): Observable<IQueryResult<IGetBoardGameByBarcodeResonse>> {
        const url = `${PUBLIC_API_URL}/queries/admin/games/${this.barcode}/get-game`;
        return from(queriesHandler.axios.get<IQueryResult<IGetBoardGameByBarcodeResonse>>(url)).pipe(
            map((result: AxiosResponse<IQueryResult<IGetBoardGameByBarcodeResonse>>) => {
                if (!result || !result.data || !result.data.resultData)
                    return {
                        resultData: {},
                        errorCode: EErrorCode.none
                    } as IQueryResult<IGetBoardGameByBarcodeResonse>;
                return result.data;
            })
        );
    }
}