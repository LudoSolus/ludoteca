<script lang="ts">
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import TextAreaInput from '$lib/components/atoms/TextAreaInput.svelte';
	import CheckboxInput from '$lib/components/atoms/CheckboxInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import { device } from '$lib/shared/hooks/useDevice';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import { untrack } from 'svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';

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

	$effect(() => {
		const snapshot = formValues ? { ...formValues } : null;

		untrack(() => {
			if (!snapshot) return;
			Object.keys(formController).forEach((key: string) => {
				const formKey = key as keyof typeof formController;
				const val = snapshot[formKey as keyof IRegisterEventRequest];
				if (inputHasValue(val)) {
					formController[formKey].touched = true;
				}
				formController[formKey].value = val;
			});
			isValid = validateForm();
		});
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
	const selectedGameIds = $derived(new Set(formValues.gamesIds));
	const allGamesSelected = $derived(boardGames.length === selectedGameIds.size);

	const statesList: SelectInputOption[] = [
		{ label: 'Acre', value: 'AC' },
		{ label: 'Alagoas', value: 'AL' },
		{ label: 'Amapá', value: 'AP' },
		{ label: 'Amazonas', value: 'AM' },
		{ label: 'Bahia', value: 'BA' },
		{ label: 'Ceará', value: 'CE' },
		{ label: 'Distrito Federal', value: 'DF' },
		{ label: 'Espírito Santo', value: 'ES' },
		{ label: 'Goiás', value: 'GO' },
		{ label: 'Maranhão', value: 'MA' },
		{ label: 'Mato Grosso', value: 'MT' },
		{ label: 'Mato Grosso do Sul', value: 'MS' },
		{ label: 'Minas Gerais', value: 'MG' },
		{ label: 'Pará', value: 'PA' },
		{ label: 'Paraíba', value: 'PB' },
		{ label: 'Paraná', value: 'PR' },
		{ label: 'Pernambuco', value: 'PE' },
		{ label: 'Piauí', value: 'PI' },
		{ label: 'Rio de Janeiro', value: 'RJ' },
		{ label: 'Rio Grande do Norte', value: 'RN' },
		{ label: 'Rio Grande do Sul', value: 'RS' },
		{ label: 'Rondônia', value: 'RO' },
		{ label: 'Roraima', value: 'RR' },
		{ label: 'Santa Catarina', value: 'SC' },
		{ label: 'São Paulo', value: 'SP' },
		{ label: 'Sergipe', value: 'SE' },
		{ label: 'Tocantins', value: 'TO' }
	];

	function validateForm(): boolean {
		if (formController.name.touched) {
			formController.name.error = validators.isFilled(formValues.name);
		}
		if (formController.description.touched) {
			formController.description.error = validators.maxLength(formValues.description, 1000);
		}
		if (formController.startDate.touched) {
			formController.startDate.error = validators.isFilled(formValues.startDate);
		}
		if (formController.finalDate.touched) {
			formController.finalDate.error = validators.isFilled(formValues.finalDate);
		}
		if (formController.street.touched) {
			formController.street.error = validators.isFilled(formValues.street);
		}
		if (formController.number.touched) {
			formController.number.error = validators.isFilled(formValues.number);
		}
		if (formController.neighborhood.touched) {
			formController.neighborhood.error = validators.isFilled(formValues.neighborhood);
		}
		if (formController.city.touched) {
			formController.city.error = validators.isFilled(formValues.city);
		}
		if (formController.state.touched) {
			formController.state.error = validators.isFilled(formValues.state);
		}
		if (formController.zipCode.touched) {
			formController.zipCode.error = validators.isFilled(formValues.zipCode);
		}

		return Object.values(formController).every(
			(field) => (field.touched || !field.required) && !field.error
		);
	}

	function onSelectAllGames(checked: boolean) {
		if (checked) {
			const allGamesIds = boardGames.map((game: IBoardGame) => game.id);
			formValues.gamesIds = allGamesIds;
		} else {
			formValues.gamesIds = [];
		}
	}

	function onGameToggle(gameId: string, checked: boolean) {
		const currentIds = [...formValues.gamesIds];
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
		formValues.gamesIds = currentIds;
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
	<div class="flex w-full sm:col-span-2">
		<FormInput
			label={'Nome'}
			placeholder="Nome do evento"
			height="90px"
			bind:value={formValues.name}
			error={formController.name.error}
		/>
	</div>

	<div class="flex w-full justify-between gap-3 sm:col-span-2 xl:gap-5">
		<FormInput
			label={'Data de Início'}
			type="datetime-local"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formValues.startDate}
			error={formController.startDate.error}
		/>

		<FormInput
			label={'Data de Término'}
			type="datetime-local"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formValues.finalDate}
			error={formController.finalDate.error}
		/>
	</div>

	<div class="w-full sm:col-span-2">
		<TextAreaInput
			label="Descrição"
			placeholder="Crie uma descrição do evento"
			bind:value={formValues.description}
			error={formController.description.error}
		/>
	</div>

	<FormInput
		label={'CEP'}
		placeholder="00000-000"
		height="90px"
		bind:value={formValues.zipCode}
		mask="zipCode"
		error={formController.zipCode.error}
	/>

	<FormInput
		label={'Rua'}
		placeholder="Nome da rua"
		height="90px"
		bind:value={formValues.street}
		error={formController.street.error}
	/>

	<div class="flex w-full justify-between gap-3 xl:gap-5">
		<FormInput
			label={'Número'}
			placeholder="123"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formValues.number}
			error={formController.number.error}
		/>

		<FormInput
			label={'Complemento'}
			placeholder="Apto 1"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formValues.supplement}
			error={formController.supplement.error}
		/>
	</div>

	<FormInput
		label={'Bairro'}
		placeholder="Bairro"
		height="90px"
		bind:value={formValues.neighborhood}
		error={formController.neighborhood.error}
	/>

	<div class="flex w-full justify-between gap-3 xl:gap-5">
		<FormInput
			label={'Cidade'}
			placeholder="Cidade"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			bind:value={formValues.city}
			error={formController.city.error}
		/>

		<SelectInput
			label={'Estado'}
			placeholder="Selecione o estado"
			width={$device == 'mobile' ? '46%' : '100%'}
			height="90px"
			options={statesList}
			bind:value={formValues.state}
			error={formController.state.error}
		/>
	</div>

	<div class="flex w-full flex-col gap-3 py-4 sm:col-span-2">
		<h4 class="font-semibold text-xl">Atrações do Evento</h4>
		<div class="flex flex-wrap gap-4">
			<CheckboxInput label="Jogos de Tabuleiro" bind:checked={formValues.hasBoardGames} />
			<CheckboxInput label="RPG" bind:checked={formValues.hasRpg} />
			<CheckboxInput label="Escape Room" bind:checked={formValues.hasEscapeRoom} />
		</div>
	</div>

	{#if formValues.hasBoardGames}
		<div class="flex w-full flex-col gap-3 py-4 sm:col-span-2">
			<div class="flex items-center justify-between">
				<h4 class="font-semibold text-xl">Selecionar Jogos de Tabuleiro</h4>
				<span class="text-xs text-gray-500">
					{formValues.gamesIds.length} selecionados
				</span>
			</div>

			<div class="mb-2 flex flex-col gap-3">
				<FormInput
					label={null}
					placeholder="Pesquisar jogos..."
					height="45px"
					bind:value={searchTerm}
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
