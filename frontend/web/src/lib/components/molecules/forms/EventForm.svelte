<script lang="ts">
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import TextAreaInput from '$lib/components/atoms/TextAreaInput.svelte';
	import CheckboxInput from '$lib/components/atoms/CheckboxInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import { device } from '$lib/shared/hooks/useDevice';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import { stringToBool } from '$lib/shared/helpers/string-to-bool';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';

	type FormField = keyof IRegisterEventRequest;

	let {
		isValid = $bindable(),
		formValues = $bindable(),
		isClicked,
		boardGames
	} = $props<{
		isValid: boolean;
		formValues: Record<keyof IRegisterEventRequest, any>;
		isClicked: boolean;
		boardGames: IBoardGame[];
	}>();

	$effect(() => {
		if (isClicked) {
			touchInputs();
			isValid = validateForm();
		}
	});

	const validators = new Validators();

	const formController: IFormController<FormField> = $state({
		name: {
			value: formValues.name,
			touched: inputHasValue(formValues.name),
			error: null,
			required: true
		},
		description: {
			value: formValues.description,
			touched: inputHasValue(formValues.description),
			error: null,
			required: true
		},
		startDate: {
			value: formValues.startDate,
			touched: inputHasValue(formValues.startDate),
			error: null,
			required: true
		},
		finalDate: {
			value: formValues.finalDate,
			touched: inputHasValue(formValues.finalDate),
			error: null,
			required: true
		},
		street: {
			value: formValues.street,
			touched: inputHasValue(formValues.street),
			error: null,
			required: true
		},
		number: {
			value: formValues.number,
			touched: inputHasValue(formValues.number),
			error: null,
			required: true
		},
		supplement: {
			value: formValues.supplement,
			touched: inputHasValue(formValues.supplement),
			error: null,
			required: false
		},
		neighborhood: {
			value: formValues.neighborhood,
			touched: inputHasValue(formValues.neighborhood),
			error: null,
			required: true
		},
		city: {
			value: formValues.city,
			touched: inputHasValue(formValues.city),
			error: null,
			required: true
		},
		state: {
			value: formValues.state,
			touched: inputHasValue(formValues.state),
			error: null,
			required: true
		},
		zipCode: {
			value: formValues.zipCode,
			touched: inputHasValue(formValues.zipCode),
			error: null,
			required: true
		},
		hasBoardGames: {
			value: formValues.hasBoardGames.toString() || 'false',
			touched: true,
			error: null,
			required: false
		},
		hasRpg: {
			value: formValues.hasRpg.toString() || 'false',
			touched: true,
			error: null,
			required: false
		},
		hasEscapeRoom: {
			value: formValues.hasEscapeRoom.toString() || 'false',
			touched: true,
			error: null,
			required: false
		},
		gamesIds: {
			value: formValues.gamesIds || [],
			touched: true,
			error: null,
			required: false
		}
	});

	let searchTerm = $state('');
	const filteredBoardGames = $derived(
		boardGames.filter((game: IBoardGame) =>
			game.name.toLowerCase().includes(searchTerm.toLowerCase())
		)
	);
	const selectedGameIds = $derived(new Set(formController.gamesIds.value as string[]));
	const allGamesSelected = $derived(boardGames.length === selectedGameIds.size);

	function validateForm(): boolean {
		if (formController.name.touched) {
			formController.name.error = validators.isFilled(formController.name.value);
		}
		if (formController.description.touched) {
			formController.description.error = validators.maxLenght(
				formController.description.value,
				1000
			);
		}
		if (formController.startDate.touched) {
			formController.startDate.error = validators.isFilled(formController.startDate.value);
		}
		if (formController.finalDate.touched) {
			formController.finalDate.error = validators.isFilled(formController.finalDate.value);
		}
		if (formController.street.touched) {
			formController.street.error = validators.isFilled(formController.street.value);
		}
		if (formController.number.touched) {
			formController.number.error = validators.isFilled(formController.number.value);
		}
		if (formController.neighborhood.touched) {
			formController.neighborhood.error = validators.isFilled(formController.neighborhood.value);
		}
		if (formController.city.touched) {
			formController.city.error = validators.isFilled(formController.city.value);
		}
		if (formController.state.touched) {
			formController.state.error = validators.isFilled(formController.state.value);
		}
		if (formController.zipCode.touched) {
			formController.zipCode.error = validators.isFilled(formController.zipCode.value);
		}

		return Object.values(formController).every(
			(field) => (field.touched || !field.required) && !field.error
		);
	}

	function onInput(formName: FormField, value: any) {
		formController[formName].touched = true;
		formController[formName].value = value;
		formValues[formName] = value;
		isValid = validateForm();
	}

	function onCheckboxChange(formName: FormField, checked: boolean) {
		formController[formName].value = checked.toString();
		formValues[formName] = checked;
		onInput(formName, checked);
	}

	function onSelectAllGames(checked: boolean) {
		if (checked) {
			const allGamesIds = boardGames.map((game: IBoardGame) => game.id);
			formController.gamesIds.value = allGamesIds;
			formValues.gamesIds = allGamesIds;
		} else {
			formController.gamesIds.value = [];
			formValues.gamesIds = [];
		}
		onInput('gamesIds', formController.gamesIds.value);
	}

	function onGameToggle(gameId: string, checked: boolean) {
		const currentIds = [...(formController.gamesIds.value as string[])];
		if (checked) {
			if (!currentIds.includes(gameId)) {
				currentIds.push(gameId);
			}
		} else {
			const index = currentIds.indexOf(gameId);
			if (index > -1) {
				currentIds.splice(index, 1);
			}
		}
		onInput('gamesIds', currentIds);
	}

	function touchInputs() {
		Object.keys(formController).forEach((key: string) => {
			formController[key as keyof IFormController<FormField>].touched = true;
		});
	}
</script>

<div
	class="grid w-full max-w-230 grid-cols-1 place-items-center gap-0 sm:grid-cols-2 sm:gap-3 xl:gap-5"
>
	<FormInput
		label={'Nome'}
		placeholder="Nome do evento"
		height="90px"
		bind:value={formController.name.value}
		error={formController.name.error}
		onInput={(value) => onInput('name', value)}
	/>

	<div class="flex w-full justify-between gap-3 sm:col-span-2 xl:gap-5">
		<FormInput
			label={'Data de Início'}
			type="datetime-local"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.startDate.value}
			error={formController.startDate.error}
			onInput={(value) => onInput('startDate', value)}
		/>

		<FormInput
			label={'Data de Término'}
			type="datetime-local"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.finalDate.value}
			error={formController.finalDate.error}
			onInput={(value) => onInput('finalDate', value)}
		/>
	</div>

	<div class="w-full sm:col-span-2">
		<TextAreaInput
			label="Descrição"
			placeholder="Crie uma descrição do evento"
			bind:value={formController.description.value}
			error={formController.description.error}
			onInput={(value) => onInput('description', value)}
		/>
	</div>

	<FormInput
		label={'CEP'}
		placeholder="00000-000"
		height="90px"
		bind:value={formController.zipCode.value}
		mask="zipCode"
		error={formController.zipCode.error}
		onInput={(value) => onInput('zipCode', value)}
	/>

	<FormInput
		label={'Rua'}
		placeholder="Nome da rua"
		height="90px"
		bind:value={formController.street.value}
		error={formController.street.error}
		onInput={(value) => onInput('street', value)}
	/>

	<div class="flex w-full justify-between gap-3 xl:gap-5">
		<FormInput
			label={'Número'}
			placeholder="123"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.number.value}
			error={formController.number.error}
			onInput={(value) => onInput('number', value)}
		/>

		<FormInput
			label={'Complemento'}
			placeholder="Apto 1"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.supplement.value}
			error={formController.supplement.error}
			onInput={(value) => onInput('supplement', value)}
		/>
	</div>

	<FormInput
		label={'Bairro'}
		placeholder="Bairro"
		height="90px"
		bind:value={formController.neighborhood.value}
		error={formController.neighborhood.error}
		onInput={(value) => onInput('neighborhood', value)}
	/>

	<div class="flex w-full justify-between gap-3 xl:gap-5">
		<FormInput
			label={'Cidade'}
			placeholder="Cidade"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.city.value}
			error={formController.city.error}
			onInput={(value) => onInput('city', value)}
		/>

		<FormInput
			label={'Estado'}
			placeholder="UF"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formController.state.value}
			error={formController.state.error}
			onInput={(value) => onInput('state', value)}
		/>
	</div>

	<div class="flex w-full flex-col gap-3 py-4 sm:col-span-2">
		<h3 class="text-sm font-semibold text-gray-700">Atrações do Evento</h3>
		<div class="flex flex-wrap gap-4">
			<CheckboxInput
				label="Jogos de Tabuleiro"
				checked={stringToBool(formController.hasBoardGames.value)}
				onChange={(checked) => onCheckboxChange('hasBoardGames', checked)}
			/>
			<CheckboxInput
				label="RPG"
				checked={stringToBool(formController.hasRpg.value)}
				onChange={(checked) => onCheckboxChange('hasRpg', checked)}
			/>
			<CheckboxInput
				label="Escape Room"
				checked={stringToBool(formController.hasEscapeRoom.value)}
				onChange={(checked) => onCheckboxChange('hasEscapeRoom', checked)}
			/>
		</div>
	</div>

	{#if stringToBool(formController.hasBoardGames.value)}
		<div class="flex w-full flex-col gap-3 py-4 sm:col-span-2">
			<div class="flex items-center justify-between">
				<h3 class="text-sm font-semibold text-gray-700">Selecionar Jogos de Tabuleiro</h3>
				<span class="text-xs text-gray-500">
					{(formController.gamesIds.value as string[]).length} selecionados
				</span>
			</div>

			<div class="mb-2 flex flex-col gap-3">
				<FormInput
					label={null}
					placeholder="Pesquisar jogos..."
					height="45px"
					bind:value={searchTerm}
					onInput={(value) => (searchTerm = value)}
				/>
				<CheckboxInput
					label={'Selecionar todos'}
					id={'select-all'}
					checked={allGamesSelected}
					onChange={(checked) => onSelectAllGames(checked)}
				/>
			</div>

			<div
				class="flex max-h-60 flex-col gap-2 overflow-y-auto rounded-md border border-gray-200 p-3"
			>
				{#if filteredBoardGames.length === 0}
					<span class="py-4 text-center text-sm text-gray-500">Nenhum jogo encontrado</span>
				{:else}
					<div class="grid grid-cols-1 gap-3 sm:grid-cols-2">
						{#each filteredBoardGames as game (game.id)}
							<button
								class="flex cursor-pointer items-center rounded-md border border-gray-100 p-2 hover:bg-gray-50"
								onclick={(e) => {
									e.preventDefault();
									e.stopPropagation();
									onGameToggle(game.id, !selectedGameIds.has(game.id));
								}}
							>
								<CheckboxInput
									label={game.name}
									id={game.id}
									checked={selectedGameIds.has(game.id)}
								/>
							</button>
						{/each}
					</div>
				{/if}
			</div>
		</div>
	{/if}
</div>
