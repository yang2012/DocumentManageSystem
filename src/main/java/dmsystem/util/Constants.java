package dmsystem.util;

/**
 * Created by justinyang on 13-12-18.
 */
public class Constants {
	// Cookie
	public static final String kCredentialsKey = "DMSystemCredentials";

	// Authorities
	public static final Integer kAdminAuthority = 2;
	public static final Integer kNormalAuthority = 1;
	public static final Integer kUnauthorizedAuthority = 0;

	// Authority of system Roles
	public static enum Authority {
		Unauthorized, Normal, Admin
	};

	// Using for user info form
	public static final String kNewPasswordField = "newPassword";
	public static final String kOldPasswordField = "oldPassword";
	public static final String kNewPasswordConfirmField = "newPasswordConfirm";

	// Using for document form
	public static final String kTitleField = "title";
	public static final String kAuthorField = "author";
	public static final String kYearField = "year";
	public static final String kPagesField = "pages";
	public static final String kAbstractsField = "abstracts";
	public static final String kKeywordsField = "keywords";
	public static final String kUrlField = "url";
	public static final String kPublisherField = "publisher";
	public static final String kDocumentTypeField = "documentType";

	// Using for creating a tag
	public static final String kTagField = "tag";

	// Using for brief comment form
	public static final String kBriefCommentField = "briefComment";

	// Using for detailed comment form
	public static final String kProblemField = "problem";
	public static final String kIdeaField = "idea";
	public static final String kExperimentField = "experiment";
	public static final String kContributionField = "contribution";
	public static final String kImprovementField = "improvement";

	// Using for add user form
	public static final String kId = "id";
	public static final String kUsernameField = "username";
	public static final String kNameField = "name";
	public static final String kAuthorityField = "authority";

	// Using for uploading attachment form
	public static final String kDocumentField = "document";
	public static final String kPptField = "ppt";
	public static final String kPosterField = "poster";
	public static final String kPresentationField = "presentation";
	public static final String kOtherField = "other";
	public static final String kCodeField = "code";

	// Using for document search
	public static final String kSearchDocumentTypeField = "documentType";
	public static final String kSearchTitleField = "title";
	public static final String kSearchAuthorField = "author";
	public static final String kSearchTagField = "tag";
	public static final String kSearchKeywordsField = "keywords";
	public static final String kSearchPublisherField = "publisher";
	public static final String kSearchYearField = "publishYear";

    // Using for evaluation type
    public static final Integer kSimpleEvaluation = 0;
    public static final Integer kDetailEvaluation = 1;
}
