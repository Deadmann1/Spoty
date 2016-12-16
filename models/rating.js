var method = Rating.prototype;

function Rating(Grade, Feedback, Date, IdUserAccount, IdLocation) {
    this.Grade = Grade;
    this.Feedback = Feedback;
	this.Date = Date;
    this.IdUserAccount = IdUserAccount;
    this.IdLocation = IdLocation;
}

module.exports = Rating;