var method = Rating.prototype;

function Rating(Grade, Feedback, IdUserAccount, IdLocation) {
    this.Grade = Grade;
    this.Feedback = Feedback;
    this.IdUserAccount = IdUserAccount;
    this.IdLocation = IdLocation;
}

module.exports = Rating;