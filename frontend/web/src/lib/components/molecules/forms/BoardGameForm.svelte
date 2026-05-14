<script lang="ts">
	import type { ICreateBoardGameRequest } from '$lib/api/commands/board-games/create-board-game/create-board-game.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import TextAreaInput from '$lib/components/atoms/TextAreaInput.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { Validators } from '$lib/shared/helpers/validators';
	import { device } from '$lib/shared/hooks/useDevice';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';

	type FormField = keyof ICreateBoardGameRequest;

	let { isValid = $bindable(), formValues = $bindable() } = $props<{
		isValid: boolean;
		formValues: Record<keyof ICreateBoardGameRequest, string>;
	}>();

	const validators = new Validators();

	const formController: IFormController<FormField> = $state({
		barcode: {
			value: formValues.barcode,
			touched: inputHasValue(formValues.barcode),
			error: null,
			required: true
		},
		title: {
			value: formValues.title,
			touched: inputHasValue(formValues.title),
			error: null,
			required: true
		},
		category: {
			value: formValues.category,
			touched: inputHasValue(formValues.category),
			error: null,
			required: true
		},
		description: {
			value: formValues.description,
			touched: inputHasValue(formValues.description),
			error: null,
			required: true
		},
		minPlayers: {
			value: formValues.minPlayers,
			touched: inputHasValue(formValues.minPlayers),
			error: null,
			required: true
		},
		maxPlayers: {
			value: formValues.maxPlayers,
			touched: inputHasValue(formValues.maxPlayers),
			error: null,
			required: true
		},
		linkInstructionManual: {
			value: formValues.linkInstructionManual,
			touched: inputHasValue(formValues.linkInstructionManual),
			error: null,
			required: true
		},
		linkVideoTutorial: {
			value: formValues.linkVideoTutorial,
			touched: inputHasValue(formValues.linkVideoTutorial),
			error: null,
			required: true
		}
	});

	const categoriesList: SelectInputOption[] = Object.entries(ECategory).map(([key, value]) => ({
		label: value,
		value: key
	}));

	function validateForm(): boolean {
		if (formController.barcode.touched) {
			formController.barcode.error = validators.isFilled(formController.barcode.value);
		}
		if (formController.title.touched) {
			formController.title.error = validators.isFilled(formController.title.value);
		}
		if (formController.category.touched) {
			formController.category.error = validators.isFilled(formController.category.value);
		}
		if (formController.description.touched) {
			formController.description.error = validators.maxLength(
				formController.description.value,
				1000
			);
		}
		if (formController.minPlayers.touched) {
			formController.minPlayers.error = validators.isFilled(formController.minPlayers.value);
		}
		if (formController.maxPlayers.touched) {
			formController.maxPlayers.error = validators.isFilled(formController.maxPlayers.value);
		}
		if (formController.linkInstructionManual.touched) {
			formController.linkInstructionManual.error = validators.url(
				formController.linkInstructionManual.value
			);
		}
		if (formController.linkVideoTutorial.touched) {
			formController.linkVideoTutorial.error = validators.url(
				formController.linkVideoTutorial.value
			);
		}

		return Object.values(formController).every(
			(field) => (field.touched || !field.required) && !field.error
		);
	}

	function onInput(formName: FormField, value: string) {
		formController[formName].touched = true;
		formValues[formName] = formController[formName].value;
		isValid = validateForm();
	}
</script>

<div
	class="grid w-full max-w-230 grid-cols-1 place-items-center gap-0 sm:grid-cols-2 sm:gap-3 xl:gap-5"
>
	<FormInput
		label={'Titulo'}
		placeholder="Titulo do jogo"
		height="90px"
		bind:value={formController.title.value}
		error={formController.title.error}
		onInput={(value) => onInput('title', value)}
	/>
	<SelectInput
		label={'Categoria'}
		placeholder={'Selecione a categoria'}
		height="90px"
		options={categoriesList}
		onChange={(value) => onInput('category', value)}
		bind:value={formController.category.value}
		error={formController.category.error}
	/>
	<div class="w-full sm:col-span-2">
		<TextAreaInput
			label="Descrição"
			placeholder="Crie uma descrição resumida do jogo"
			bind:value={formController.description.value}
			error={formController.description.error}
			onInput={(value) => onInput('description', value)}
		/>
	</div>

	<FormInput
		label={'Código de Barras'}
		placeholder="Escaneie o código de barras"
		type="number"
		min={0}
		height="90px"
		bind:value={formController.barcode.value}
		error={formController.barcode.error}
		onInput={(value) => onInput('barcode', value)}
	/>

	<div class="flex w-full justify-between gap-3 xl:gap-5">
		<FormInput
			label={'Mín. de Jogadores'}
			placeholder="0"
			min={0}
			type="number"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.minPlayers.value}
			error={formController.minPlayers.error}
			onInput={(value) => onInput('minPlayers', value)}
		/>

		<FormInput
			label={'Máx. de jogadores'}
			placeholder="10"
			min={0}
			type="number"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.maxPlayers.value}
			error={formController.maxPlayers.error}
			onInput={(value) => onInput('maxPlayers', value)}
		/>
	</div>

	<FormInput
		label={'Link do Manual de Instruções'}
		placeholder="Cole o link do manual de instruções"
		height="90px"
		bind:value={formController.linkInstructionManual.value}
		error={formController.linkInstructionManual.error}
		onInput={(value) => onInput('linkInstructionManual', value)}
	/>

	<FormInput
		label={'Link do Tutorial'}
		placeholder="Cole o link do video do tutorial"
		height="90px"
		bind:value={formController.linkVideoTutorial.value}
		error={formController.linkVideoTutorial.error}
		onInput={(value) => onInput('linkVideoTutorial', value)}
	/>
</div>
