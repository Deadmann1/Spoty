var method = Rating.prototype;

function Rating(Grade, Feedback,Date, IdUserAccount, IdLocation) {
    this.Grade = Grade;
    this.Feedback = Feedback;
    this.IdUserAccount = IdUserAccount;
    this.IdLocation = IdLocation;
	this.Date = Date;
}

module.exports = Rating;