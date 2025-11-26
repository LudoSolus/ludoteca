export interface IInputController{
    value: string
    error: string |null
    touched: boolean
    required: boolean
}

export type IFormController<T extends string> = {
    [key in T]: IInputController
}