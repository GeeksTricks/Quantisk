from .repositories import user_repo
from flask_httpauth import HTTPBasicAuth
from werkzeug.security import check_password_hash


auth = HTTPBasicAuth()


@auth.verify_password
def verify_password(login, password):

    user = user_repo.get_by_login(login)
    if user:
        # return user.pass_hash == generate_password_hash(password)
        print(user.pass_hash)
        return check_password_hash(user.pass_hash, password)
    return False
