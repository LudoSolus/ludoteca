<script lang="ts">
	import { UpdateUserCommand } from '$lib/api/commands/users/update-user/update-user.command';
	import type { IUpdateUserRequest } from '$lib/api/commands/users/update-user/update-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import { ListEducationalInstitutionsQuery } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.query';
	import type { IGetUserProfileDetailsResponse } from '$lib/api/queries/users/get-user-profile-details/get-user-profile-details.interface';
	import { GetUserProfileDetailsQuery } from '$lib/api/queries/users/get-user-profile-details/get-user-profile-details.query';
	import Profile from '$lib/components/templates/Profile.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import { authService } from '$lib/shared/stores/auth';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	let userData: IGetUserProfileDetailsResponse | null = $state(null);

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let educationalInstitutions: IEducationalInstitution[] = $state([]);
	let isLoading: boolean = $state(false);

	onMount(() => {
		fetchEducationalInstitutions();
		fetchProfileData();
	});

	function fetchEducationalInstitutions() {
		queriesHandler.handle(new ListEducationalInstitutionsQuery()).subscribe({
			next: (data) => {
				educationalInstitutions = data.resultData;
			},
			error: (err) => {
				toast.error('Erro ao buscar instituições, recarregue a página.', { closable: true });
			}
		});
	}

	function fetchProfileData() {
		queriesHandler.handle(new GetUserProfileDetailsQuery()).subscribe({
			next: (data) => {
				userData = data.resultData;
			},
			error: (err) => {
				toast.error('Erro ao buscar instituições, recarregue a página.', { closable: true });
			}
		});
	}

	function updateUser(userData: IUpdateUserRequest) {
		isLoading = true;
		commandsHandler.handle(new UpdateUserCommand(userData)).subscribe({
			next: (data) => {
				toast.success('Usuário atualizado com sucesso!', { closable: true });
				const token = data.data.resultData;
				authService.login(token);
				isLoading = false;
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<Profile {isLoading} {educationalInstitutions} {userData} onEdit={updateUser} />
