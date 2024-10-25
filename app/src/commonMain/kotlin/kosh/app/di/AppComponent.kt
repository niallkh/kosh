package kosh.app.di

public interface AppComponent {

    public val debug: Boolean

    public val bugsnagKey: String

    public val reownProject: String

    public val groveKey: String
}
