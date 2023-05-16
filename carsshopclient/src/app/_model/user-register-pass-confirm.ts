export class UserRegisterPassConfirm{
    username?: string;
    password?: string;
    passwordConfim?: string;

    constructor(username: string, password: string, passwordConfirm: string) {
        this.username = username;
        this.password = password;
        this.passwordConfim = passwordConfirm;
    }
}