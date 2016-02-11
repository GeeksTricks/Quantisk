# from functools import wraps
# from flask import request, Response
from .repositories import user_repo
#
# def check_auth(username, password):
#     return username == 'admin' and password == 'secret'
#
# def authenticate():
#     return Response(
#     'Could not verify your access level for that URL.\n'
#     'You have to login with proper credentials', 401,
#     {'WWW-Authenticate': 'Basic realm="Login Required"'})
#
# def requires_auth(f):
#     @wraps(f)
#     def decorated(*args, **kwargs):
#         auth = request.authorization
#         if not auth or not check_auth(auth.username, auth.password):
#             return authenticate()
#         return f(*args, **kwargs)
#     return decorated

from flask_httpauth import HTTPBasicAuth
auth = HTTPBasicAuth()

@auth.verify_password
def verify_password(login, password):

    user = user_repo.get_by_login(login)
    if user:
        return user.password == password
    return False

