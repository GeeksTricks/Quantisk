from .repositories import user_repo
from werkzeug.security import check_password_hash
from functools import wraps
from flask import request, jsonify, g


ROLES = {
    'Admin': 1,
    'User': 2,
}
ADMIN = ROLES['Admin']
USER  = ROLES['User']

def verify_password(login, password):
    user = user_repo.get_by_login(login)
    if user:
        if check_password_hash(user.pass_hash, password):
            return user
    return None


def unauthorized(message='Unauthorized access'):
    response = jsonify(message=message)
    response.status_code = 401
    response.headers = {'WWW-Authenticate': 'Basic realm="Login Required"'}
    return response


def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if auth:
            user = verify_password(auth.username, auth.password)
            if user:
                g.user = user
                return f(*args, **kwargs)
        return unauthorized()
    return decorated


def requires_roles(*roles):
    def wrapper(f):
        @wraps(f)
        def wrapped(*args, **kwargs):
            if g.user.role not in roles:
                return unauthorized('WRONG ROLE!!!')
            return f(*args, **kwargs)
        return wrapped
    return wrapper
