"""
월별 총 자기소개서 작성 수 / 공고 수 출력
"""
import requests
import datetime

headers = { 'Content-Type': 'application/json'}

# 직무, 질문
def get_questions(employment_id) :
    url = "https://jasoseol.com/employment/employment_question.json"
    payload = "{\n\t\"employment_id\": "+str(employment_id)+"\n}"

    response = requests.request("POST", url, headers=headers, data = payload)
    data = response.json()

    return data["employment_question"] or []

# 공고 상세
def get_recruit_detail(recruit_id) :
    url = "https://jasoseol.com/employment/get.json"
    payload = "{\n\t\"employment_company_id\": "+str(recruit_id)+"\n}"

    response = requests.request("POST", url, headers=headers, data = payload)
    data = response.json()
    
    return { "content": data["content"],
                "employment_page_url" : data["employment_page_url"],
                "employments" : data["employments"],
                "recruit_type" : data["recruit_type"]
            }

# 채용공고 
def get_recruits() :
    url = "https://jasoseol.com/employment/calendar_list.json"
    payload = "{\n\t\"start_time\": \"2014-01-01T15:00:00.000Z\",\n\t\"end_time\": \"2020-03-01T15:00:00.000Z\"\n}"

    response = requests.request("POST", url, headers=headers, data = payload)
    recuit = response.json()

    print("채용공고 크롤링 시작합니다.")
    for data in recuit["employment"] :
        recruit_id = data["id"]



        _start_time = data["start_time"].split('T')
        _date = list(map(int, _start_time[0].split("-")))
        _time = list(map(int, _start_time[1].split(".")[0].split(":")))
        start_time = datetime.datetime(_date[0], _date[1], _date[2], _time[0], _time[1], _time[2])

        _end_time = data["end_time"].split('T')
        _date = list(map(int, _end_time[0].split("-")))
        _time = list(map(int, _end_time[1].split(".")[0].split(":")))
        end_time = datetime.datetime(_date[0], _date[1], _date[2], _time[0], _time[1], _time[2])
        
        detail = get_recruit_detail(recruit_id)
        employment_page_url = detail["employment_page_url" ]
        content = detail["content"]
        recruit_type = detail["recruit_type"]
        view_count = detail["view_count"]


        for employment in detail["employments"] :
            position_id = employment["id"]
            field = employment["field"]
            division= employment["division"]

            """ position table """
            curs = conn.cursor()
            sql = """INSERT INTO position VALUES (%s, %s, %s, %s)"""
            curs.execute(sql,(position_id, recruit_id, field, division))
            conn.commit()

        
            questions = get_questions(position_id)
            for question in questions :
                question_id = question["id"]
                question_content = question["question"] or '자유양식'
                question_limit = question["total_count"] or 0

                """ question table """
                curs = conn.cursor()
                sql = """INSERT INTO question VALUES (%s, %s, %s, %s)"""
                curs.execute(sql, (question_id, position_id, question_content, question_limit))
                conn.commit()

    print("완료!")



# get_recruits()

year = [y for y in range(2014, 2021)]
month = [m for m in range(1, 13)]
for y in year :
    for m in month :

    
# date = [m for m in range(2014, 2021)]

user_avg = {}

# 2014-01