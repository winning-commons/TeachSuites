import requests

url = "http://108.30.159.119:8443"


def testCreateClass():
    class_id = "12345"
    class_name = "Test Class 2"
    class_description = "This is a test class"
    teacher_id = "91234790-8f47-11e9-bc42-526af7764f64"  # Use the known UUID

    endpoint = url + "/api/make-class"
    body = {
        "google-classroom-id": class_id,
        "class-name": class_name,
        "class-description": class_description,
        "teacher-id": teacher_id,
    }
    response = requests.post(endpoint, json=body)
    print(response.json())
    assert response.status_code == 200


def testGetClasses():
    teacher_id = "91234790-8f47-11e9-bc42-526af7764f64"  # Use the known UUID
    body = {"teacher-id": teacher_id}
    endpoint = url + "/api/get-classes"
    response = requests.post(endpoint, json=body)
    print(response.json())


def testCreateMC():
    endpoint = url + "/create-mc"
    body = {
        "sa-topic": "France",
    }
    response = requests.post(endpoint, json=body)
    print(response)


def testGetStudentClasses():
    student_id = "b1f19de4-8ba4-45b8-b748-cd0e6509a44a"
    endpoint = url + "/api/get-student-classes"
    body = {"student-id": student_id}
    response = requests.post(endpoint, json=body)
    print(response.json())


def testGetExams():
    student_id = "b1f19de4-8ba4-45b8-b748-cd0e6509a44a"
    endpoint = url + "/api/get-exams"
    body = {"student-id": student_id}
    response = requests.post(endpoint, json=body)
    print(response.json())


def testGetQs():
    exam_id = "f02490c6-e276-47c3-88ed-233451850762"
    endpoint = url + "/api/get-exam-qs"
    body = {"exam-id": exam_id}
    response = requests.post(endpoint, json=body)
    print(response.json())


# testGetStudentClasses()
# testGetExams()
testGetQs()
# testGetClasses()
# testCreateClass()
# testCreateMC()
