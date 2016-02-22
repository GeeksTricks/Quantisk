from .repositories import user_repo
from werkzeug.security import check_password_hash
from functools import wraps
from flask import request, jsonify, g


def verify_password(login, password):
    user = user_repo.get_by_login(login)
    if user:
        pass_hash = user_repo.get_pass_hash(user.id)
        if check_password_hash(pass_hash, password):
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


