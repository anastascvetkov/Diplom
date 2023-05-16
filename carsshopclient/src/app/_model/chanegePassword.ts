export class ChangePassword{
    username?: string;
    oldPassword?: string;
    newPassword?: string;
    newPasswordAgain?: string;

    constructor(username: string, oldPassword: string, newPassword: string, newPasswordAgain: string){
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordAgain = newPasswordAgain;
    }
}