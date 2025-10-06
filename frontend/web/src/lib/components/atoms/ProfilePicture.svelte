<script lang="ts">
	export let userName: string;

	function getUserAbbreviation() {
		let users: string[] = userName.split('');
		return users[0][0] + (users[1][0] ? users[1][0] : users[0][1]);
	}
</script>

<div class="profile-box flex items-center justify-center">
	<p>{getUserAbbreviation()}</p>
</div>

<style>
	.profile-box {
		border-radius: 100px;
		border: 2px solid #000e3d;
		background-color: #7e90fe;
		width: 50px;
		height: 50px;
	}

	.profile-box p {
        font-family: "Inter", sans-serif;
		text-transform: uppercase;
		color: white;
        font-size: 20px;
        font-weight: 600;
	}
</style>
