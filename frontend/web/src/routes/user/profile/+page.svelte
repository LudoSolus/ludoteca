<script lang="ts">
	import { UpdateUserCommand } from '$lib/api/commands/users/update-user/update-user.command';
	import type { IUpdateUserRequest } from '$lib/api/commands/users/update-user/update-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import { ListEducationalInstitutionsQuery } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.query';
	import type { IGetUserProfileDetailsResponse } from '$lib/api/queries/users/get-user-profile-details/get-user-profile-details.interface';
	import Profile from '$lib/components/templates/Profile.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const userDataMock: IGetUserProfileDetailsResponse = {
		publicId: 'CCMC',
		name: 'anderso dos Santos',
		cpf: '0212675516',
		instituitionId: '16610773-99dd-4df0-901f-041a3936d5d6',
		phone: '44995869381',
		email: 'fee@gmail.com',
        ra: "2565838",
		birthDate: new Date()
	};

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let educationalInstitutions: IEducationalInstitution[] = $state([]);
	let isLoading: boolean = $state(false);

	onMount(() => {
		fetchEducationalInstituitions();
	});

	function fetchEducationalInstituitions() {
		queriesHandler.handle(new ListEducationalInstitutionsQuery()).subscribe({
			next: (data) => {
				educationalInstitutions = data.resultData;
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
				console.log(data);
				// const token = data.data.resultData;
				// authService.login(token);
				isLoading = false;
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<Profile {isLoading} {educationalInstitutions} userData={userDataMock} onEdit={updateUser} />
