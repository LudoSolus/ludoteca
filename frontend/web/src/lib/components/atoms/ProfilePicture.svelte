<script lang="ts">
	export let userName: string;
	export let width: string = '50px';
	export let height: string = '50px';
	export let fontSize: string = '20px';
	export let onClick: (() => void) | null = null;

	$: userAbbreviation = () => {
		let users: string[] = userName.split(' ');
		return users[0][0] + (users[1][0] ? users[1][0] : users[0][1]);
	}
</script>

<button
	class="profile-box flex items-center justify-center"
	style="width: {width}; height: {height}; cursor: {onClick ? 'pointer' : 'default'}"
	on:click={onClick}
>
	<p style="font-size: {fontSize};">{userAbbreviation()}</p>
</button>

<style>
	.profile-box {
		border-radius: 100px;
		border: 2px solid #000e3d;
		background-color: #7e90fe;
	}

	.profile-box p {
		font-family: 'Inter', sans-serif;
		text-transform: uppercase;
		color: white;
		font-weight: 600;
	}
</style>
