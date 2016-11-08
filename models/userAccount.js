var method = UserAccount.prototype;

function UserAccount(IdUserAccount, Username, Password, Firstname,  Lastname, Birthdate, IdAccountType) {
    this.IdUserAccount = IdUserAccount;
    this.Username = Username;
    this.Password = Password;
    this.Firstname = Firstname;
    this.Lastname = Lastname;
    this.Birthdate = Birthdate;
    this.IdAccountType = IdAccountType;
}

module.exports = UserAccount;