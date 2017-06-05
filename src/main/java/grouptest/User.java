package grouptest;

/**
 * Created by Thomas Ecalle on 26/05/2017.
 */
public final class User
{
    private long id;
    private String pseudo;
    private String email;
    private String password;

    private User(Builder builder)
    {
        this.id = builder.id;
        this.pseudo = builder.pseudo;
        this.email = builder.email;
        this.password = builder.password;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }


    public static class Builder
    {
        long id;
        String pseudo;
        String email;
        String password;

        public Builder id(long id)
        {
            this.id = id;
            return this;
        }

        public Builder pseudo(String pseudo)
        {
            this.pseudo = pseudo;
            return this;
        }

        public Builder email(String email)
        {
            this.email = email;
            return this;
        }

        public Builder password(String password)
        {
            this.password = password;
            return this;
        }

        public User build()
        {
            return new User(this);
        }

    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPseudo()
    {
        return pseudo;
    }

    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    @Override
    public String toString()
    {
        return "User[id : " + id + ", pseudo : " + pseudo + ", password : " + password + ", email : " + email + "]";
    }
}
